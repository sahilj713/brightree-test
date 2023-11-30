pipeline {
 agent {label 'jenkins-slave'}

  stages{
    stage('Run test case'){
      steps{
        script{
          mvn clean install
        }
      }
    }
  }
}
