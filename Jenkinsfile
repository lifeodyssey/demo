def APP_ENV='dev'
def  MONGODB_URI= 'mongodb://dev_mongo_username:dev_mongo_pwd@host.docker.internal:27018/?authSource=admin\\&tls=false'

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
            steps{
                script{
                    buildDockerImage.build(imageName:"demo:latest",svcName:"book-svc")

                }
            }
        }
        stage('Dev') {
            steps {
                script {
                    deployApp(APP_ENV:APP_ENV,MONGODB_URI:MONGODB_URI)
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
                    deployApp(APP_ENV:APP_ENV,MONGODB_URI:MONGODB_URI)                }
            }
        }
    }
}
