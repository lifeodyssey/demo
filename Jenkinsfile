pipeline
{
  agent any
  parameters{
  string(name:'SPRING_CONFIG_NAME', defaultValue:'dev',description:'Spring config name')
  }
  stages
  {
    stage('Build')
    {
      steps
      {
        sh"./gradlew clean build --info"
      }
    }
      stage('Dev')
    {
      steps
      {
         sh" chmod +x deploy.sh"
         sh" ./deploy.sh ${params.SPRING_CONFIG_NAME}"
      }
    }
      stage('Deploy to QA approval')
    {
      steps
      {
        input "Deploy to QA?"
        script
        {
                  env.SPRING_CONFIG_NAME = 'qa'
        }
      }
    }
      stage('QA')
    {
      steps
      {
         sh" chmod +x deploy.sh"
         sh" ./deploy.sh ${params.SPRING_CONFIG_NAME}"
      }
    }
  }
}
