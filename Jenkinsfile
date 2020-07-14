
pipeline{
 agent any
   stages{
     stage('Build'){
       steps{
         echo "Building project..."
         bat "mvn clean"
       }
     }
     stage('Test'){
       steps{
         echo "Testing project..."
         bat "mvn test"
       }
     }
     stage('Compile'){
       steps{
         echo "Compliing project..."
         bat "mvn compile"
       }
     }
     stage('Package'){
       steps{
         echo "Packing the project into jar..."
         bat "mvn package"
       }
     }
     
     stage('Deploy'){
       steps{
         echo "Deploying project..."
       }
     }

    
    }
 
 

}
