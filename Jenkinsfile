//def APP_ENV
//def MONGODB_URI
def dockerImageName = "demo:latest"

@Library('jenkins-shared-library') _
pipeline {
    agent any
    stages {
        stage('Test and Build Jar') {
            steps {
                sh "./gradlew clean build"
            }
        }
        stage('Build Image') {
            script{

                buildDockerImage(dockerImageName,'book-svc')
            }
        }
        stage('Dev') {
            steps {
                script {
                    def config = loadConfiguration('DEV')
                    deployApp(config.APP_ENV, config.MONGODB_URI, dockerImageName)
                }
            }
        }
        stage('Deploy to QA approval') {
            steps {
                input "Deploy to QA?"
            }
        }
        stage('QA') {
            steps {
                script {
                    def config = loadConfiguration('QA')
                    deployApp(config.APP_ENV, config.MONGODB_URI, dockerImageName)
                }
            }
        }
    }
}
