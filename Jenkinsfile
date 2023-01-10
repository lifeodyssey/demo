def SPRING_CONFIG_NAME = 'dev'
def NGINX_PORT = 81
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh "./gradlew clean build --info"
            }
        }
        stage('Dev') {
            steps {
                script {
                    sh " docker build -t demo . --build-arg SPRING_CONFIG_NAME=$SPRING_CONFIG_NAME"
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $SPRING_CONFIG_NAME $NGINX_PORT"
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
                    SPRING_CONFIG_NAME ='qa'
                    NGINX_PORT='82'
                    sh "docker build -t demo . --build-arg SPRING_CONFIG_NAME=$SPRING_CONFIG_NAME"
                    sh " chmod +x deploy.sh"
                    sh " ./deploy.sh $SPRING_CONFIG_NAME $NGINX_PORT"
                }
            }
        }
    }
}
