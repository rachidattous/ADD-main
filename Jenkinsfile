pipeline {
    agent any

    stages {

        stage('Get Logs') {
            input {
                message "Select a microservice: "
                ok "Done"
                parameters {
                    choice(name: 'label', choices: ['auth', 'course', 'notification', 'search', 'file'], description: '')
                }
            }
            steps {
                script {
                    def podName = sh(returnStdout: true, script: "kubectl get pods -l app=${label} -o custom-columns=:metadata.name").trim()

                    echo "Pod Name: ${podName}"

                    sh "kubectl logs --tail 1000 ${podName}"

                }
            }
        }
    }
}
