pipeline {
    agent any
    stages {
        stage('Maven Clean') {
            steps {
                bat "mvn clean"
            }
        }
        stage('Maven Test') {
            steps {
                bat "mvn test"
            }
        }
        stage('Maven Package') {
            steps {
                bat "mvn package"
            }
        }
    }
    post {
        always {
            bat "copy %JENKINS_HOME%\jobs\%JOB_NAME%\builds\%BUILD_NUMBER\log C:\Users\Collabera\Documents\Logs\log.txt%"
        }
    }
}
