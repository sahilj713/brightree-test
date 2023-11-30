pipeline {
 agent {label 'jenkins-slave'}

  stages{
    stage('Run test case'){
      steps{
        sh 'sudo mvn -DskipTests clean install'
      }
    }
  }
}
