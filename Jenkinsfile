timeout(30) {
        node("(built-in)") {
            echo "Download project"
            checkout scm: [
                $class: 'GitSCM',
                branches: [[name: 'master']],
                userRemoteConfigs: [[
                    credentials: 'f9c62664-b05c-41c4-b7c0-3b4b36510faf',
                    url: 'git@github.com:Alaiv/api-testing.git'
                ]]
            ]

        labelledShell(label: 'Run tests', script: '''
            mvn clean test
        ''')
    }
}