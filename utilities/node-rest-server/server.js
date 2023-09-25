import express from "express";
import jwt from "jsonwebtoken";
import { Low } from "lowdb";
import { JSONFile } from "lowdb/node";

const brightreeSiteEndpoint = "/api/site/some_site";
const brightreeClientId = "dcJHOFvMijRmNolrkZQveVcvNTwyGMcx";

const brightreeClientSecret =
  "IcgwuDHpXhdulbqykiXDBWqetwQjgsBxQdUJldLRQSNBzvPoBdXnCSXKJfmEEOUi";

const brightreeGrantType = "client_credentials";
const brightreeExpiresIn = 86399;

const resDotSendInterceptor = (res, send) => (content) => {
  res.contentBody = content;
  res.send = send;
  res.send(content);
};

const requestLoggerMiddleware = (req, res, next) => {
  console.log("req: ", req.method, req.body);
  res.send = resDotSendInterceptor(res, res.send);

  res.on("finish", () => {
    console.log("res: ", res.statusCode, res.contentBody, "\n");
  });

  next();
};

const app = express();
app.use(express.json({ limit: "200mb" }));
app.use(express.urlencoded({ extended: true }));
app.use(requestLoggerMiddleware);

const adapter = new JSONFile("db.json");
const db = new Low(adapter);

async function startServer() {
  await db.read();
  db.data = { brightree: { referrals: [] } };

  app.post("/auth/token", async (req, res, next) => {
    // Generate Token
    if (
      !req.body ||
      req.body.client_id !== brightreeClientId ||
      req.body.client_secret !== brightreeClientSecret ||
      req.body.grant_type !== brightreeGrantType
    ) {
      const response = {
        error: "invalid_client",
      };

      return res.status(400).json(response);
    }

    const createdAt = new Date().toISOString();

    const accessToken = jwt.sign({ status: "success", createdAt }, "secret", {
      expiresIn: brightreeExpiresIn,
    });

    const tokenResponse = {
      access_token: accessToken,
      token_type: "bearer",
      expires_in: brightreeExpiresIn,
    };

    const expirationTimestamp =
      new Date().getTime() + brightreeExpiresIn * 1000;

    db.data.brightree.token = {
      accessToken,
      expirationTimestamp,
    };

    await db.write();

    return res.status(200).json(tokenResponse);
  });

  function isAuthenticated(req) {
    if (!db.data.brightree.token) {
      return false;
    }

    let bearerHeader = req.headers["authorization"];

    if (!bearerHeader) {
      bearerHeader = req.headers["Authorization"];
    }

    if (!bearerHeader) {
      return false;
    }

    const bearer = bearerHeader.split(" ");
    const bearerToken = bearer[1];

    try {
      const decodedJwt = jwt.verify(bearerToken, "secret");

      if (
        db.data.brightree.token.accessToken !== bearerToken ||
        decodedJwt.exp > new Date().getTime()
      ) {
        return false;
      }

      return true;
    } catch (error) {
      console.error(error);
    }

    return false;
  }

  app.post(brightreeSiteEndpoint + "/referral", async (req, res, next) => {
    if (!isAuthenticated(req)) {
      const response = {
        error: "invalid_token",
      };

      return res.status(401).json(response);
    }

    if (!req.body || !req.body.sendingFacility) {
      const response = {
        message: {
          name: "Invalid request",
          message: req.body + " is invalid",
          status: "failed",
        },
      };

      return res.status(400).json(response);
    }

    const referralKey = db.data.brightree.referrals.length;
    const referral = { referralKey, req: `${req.body}` };

    db.data.brightree.referrals.push(referral);
    console.log("db -> created referral: ", referral);
    await db.write();

    return res.status(200).json({
      Messages: [],
      ReferralKey: referralKey,
    });
  });

  app.post(
    brightreeSiteEndpoint + "/referral/:referralKey/document",
    async (req, res, next) => {
      if (!isAuthenticated(req)) {
        const response = {
          error: "invalid_token",
        };

        return res.status(401).json(response);
      }

      const referralKey = req.params.referralKey;

      if (!referralKey) {
        const response = {
          message: {
            name: "Invalid referralKey",
            status: "failed",
          },
        };

        return res.status(400).json(response);
      }

      const referral = db.data.brightree.referrals.find(
        (referral) => referral.referralKey === Number(referralKey)
      );

      if (!referral) {
        const response = {
          message: {
            name: "some_site does not have access to this referral or the referral does not exist.",
            status: "failed",
          },
        };

        return res.status(400).json(response);
      }

      if (!req.body || !req.body.id) {
        const response = {
          message: {
            name: "Invalid request.",
            status: "failed",
          },
        };

        return res.status(400).json(response);
      }

      return res.status(200).send("success");
    }
  );

  app.get(
    brightreeSiteEndpoint + "/referral/:referralKey/getreferralstatus",
    async (req, res, next) => {
      if (!isAuthenticated(req)) {
        const response = {
          error: "invalid_token",
        };

        return res.status(401).json(response);
      }

      const referralKey = req.params.referralKey;

      if (!referralKey) {
        const response = {
          message: {
            name: "Invalid referralKey",
            status: "failed",
          },
        };

        return res.status(400).json(response);
      }

      const referral = db.data.brightree.referrals.find(
        (referral) => referral.referralKey === Number(referralKey)
      );

      if (!referral) {
        const response = {
          message: {
            name: "some_site does not have access to this referral or the referral does not exist.",
            status: "failed",
          },
        };

        return res.status(400).json(response);
      }

      const response = {
        ReferralStatus: "New",
        RejectionReason: null,
        ReferralSalesOrder: null,
        ReferralPatient: null,
        SleepTherapies: [],
      };

      return res.status(200).json(response);
    }
  );

  app.listen(3001, () => {
    console.log("server running 0.0.0.0:3001");
  });
}

startServer();
