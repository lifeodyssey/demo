def APP_ENV
def MONGODB_URI
pipeline {
    agent any
    stages {
        stage('Test and Build Jar') {
            steps {
                sh "./gradlew clean build --info"
            }
        }
        stage('Build Image') {
            steps {
                sh " docker build -t demo:latest . "
            }
        }
        stage('Dev') {
            steps {
                script {
                    APP_ENV =env.DEV_CONFIG
                    MONGODB_URI=env.DEV_MONGO_URI
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $APP_ENV $MONGODB_URI"
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
                    APP_ENV =env.QA_CONFIG
                    MONGODB_URI=env.QA_MONGO_URI
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $APP_ENV $MONGODB_URI"
                }
            }
        }
    }
}
