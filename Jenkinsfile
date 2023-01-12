def SPRING_CONFIG_NAME
def NGINX_PORT
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
                    SPRING_CONFIG_NAME =env.DEV_CONFIG
                    NGINX_PORT=env.DEV_NGINX_PORT
                    MONGODB_URI=env.DEV_MONGO_URI
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $SPRING_CONFIG_NAME $NGINX_PORT $MONGODB_URI"
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
                    SPRING_CONFIG_NAME =env.QA_CONFIG
                    NGINX_PORT=env.QA_NGINX_PORT
                    MONGODB_URI=env.QA_MONGO_URI
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $SPRING_CONFIG_NAME $NGINX_PORT $MONGODB_URI"
                }
            }
        }
    }
}
