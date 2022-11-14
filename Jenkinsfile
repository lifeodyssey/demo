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
         sh" docker run -d -p 8000:8000 demo:latest  "
      }
    }
  }
}
