pipeline
{
  agent any
  stages {
    stage('Build')
    {
      steps {
        sh"./gradlew clean build --info"
      }
    }
      stage('Deploy')
    {
      steps {
         sh" docker build -t demo . "
         sh" chmod +x deploy.sh"
         sh" ./deploy.sh"
      }
    }
  }
}
