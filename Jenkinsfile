pipeline
{  //must be top level
  agent any
  stages {  //where the work happens
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
         //sh" docker run -dp 127.0.0.1:8000:8000 demo:latest --restart=on-failure  --name pipeline_demo "
         //  sh"docker compose up -d"
         sh" docker run -d -p 8000:8000 demo:latest  "

      }
    }
  }
}
