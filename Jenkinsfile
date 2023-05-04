
@Library('jenkins-shared-library') _

pipeline {
    agent any
    stages {
        stage('Test and Build Jar') {
            steps {
                script{
                    buildGradle()
                }
            }
        }
        stage('Build Image') {
            steps{
                script{
                    buildDockerImage(imageName:"demo:latest",svcName:"book-svc")

                }
            }
        }
        stage('Dev') {
            steps {
                script {
                    deployApp(APP_ENV:'DEV')
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
                    deployApp(APP_ENV:'QA')                }
            }
        }
    }
}
