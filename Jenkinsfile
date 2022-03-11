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
}
