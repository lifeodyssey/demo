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
         sh" docker build -t pipeline_demo . "
         sh" docker run -dp 8000:8000 pipeline_demo:latest --restart=on-failure  --name pipeline_demo "
         //  sh"docker compose up -d"
      }
    }
  }
}
