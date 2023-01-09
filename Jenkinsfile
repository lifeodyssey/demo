def SPRING_CONFIG_NAME='dev'
pipeline
        {
            agent any
            stages
                    {
                        stage('Build')
                                {
                                    steps
                                            {
                                                sh "./gradlew clean build --info"
                                            }
                                }
                        stage('Dev')
                                {
                                    steps
                                            {
                                                script
                                                        {
                                                            SPRING_CONFIG_NAME = 'dev'
                                                        }
                                                sh " chmod +x deploy.sh"
                                                sh " ./deploy.sh ${SPRING_CONFIG_NAME}"
                                            }
                                }
                        stage('Deploy to QA approval')
                                {
                                    steps
                                            {
                                                input "Deploy to QA?"
                                            }
                                }
                        stage('QA')
                                {
                                    steps
                                            {
                                                script
                                                        {
                                                            SPRING_CONFIG_NAME = 'qa'
                                                        }
                                                sh " chmod +x deploy.sh"
                                                sh " ./deploy.sh ${SPRING_CONFIG_NAME}"
                                            }
                                }
                    }
        }
