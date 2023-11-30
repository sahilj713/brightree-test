pipeline {
 agent {label 'jenkins-slave'}

  stages{
    stage('Run test case'){
      steps{
        // sh 'sudo apt update'
        // sh 'sudo apt install maven'
        sh 'sudo mvn -DskipTests clean install'
      }
    }
  }
}
