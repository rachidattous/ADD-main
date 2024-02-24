#!/usr/bin/env groovy

def TestProject(microserviceName){
    sh "cd add-backend/${microserviceName} && mvn test"
}

def BuildJar(microserviceName){
    sh "cd add-backend/${microserviceName} && mvn clean package -DskipTests"
}

def BuildDockerImage(repoName,microserviceName){
    sh "docker build -t 365583262659.dkr.ecr.eu-west-3.amazonaws.com/${repoName}:latest -f add-backend/${microserviceName}/Dockerfile ."
}

def EcrLogin(){
    sh 'aws ecr get-login-password --region eu-west-3 | docker login --username AWS --password-stdin 365583262659.dkr.ecr.eu-west-3.amazonaws.com'
}

def CreateOrCheckECRRepository(ecrRepository, region) {
    // Check if the ECR repository exists
    def repositoryExists = sh(script: "aws ecr describe-repositories --repository-names ${ecrRepository}", returnStatus: true)

    if (repositoryExists == 0) {
        echo "ECR repository '${ecrRepository}' already exists."
    } else {
        // Create the ECR repository if it does not exist
        sh "aws ecr create-repository --repository-name ${ecrRepository} --region ${region}"
        echo "ECR repository '${ecrRepository}' created successfully."
    }
}

def ResetDockerImageOnECR(ecrRepository, imageTag) {
    // AWS CLI command to delete the image
    sh "aws ecr batch-delete-image --repository-name ${ecrRepository} --image-ids imageTag=${imageTag}"

    // Verify the deletion status
    def result = sh(script: "aws ecr describe-images --repository-name ${ecrRepository}", returnStatus: true, returnStdout: true)

    if (result == 1) {
        error "Failed to delete the image"
    } else {
        echo "Image ${imageTag} deleted successfully from ECR repository ${ecrRepository}"
        sh "docker push 365583262659.dkr.ecr.eu-west-3.amazonaws.com/${ecrRepository}:${imageTag}"
    }
}

def ResetDeployment(deploymentName) {
    // Check if the deployment exists
    def deploymentExists = sh(script: "kubectl get deployment ${deploymentName}", returnStatus: true)

    if (deploymentExists == 0) {
        // Deployment exists, so delete it
        sh "kubectl delete deployment ${deploymentName}"
        echo "Deployment ${deploymentName} deleted successfully."
    } else {
        echo "Deployment ${deploymentName} does not exist."
    }
}


def DeployEKS(){
    sh "kubectl apply -f add-backend/course/k8s/deployemen-service.yaml"
}

return this
