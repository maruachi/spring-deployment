pipeline {
    agent any
    parameters {
        string(name : 'RELEASE_BRANCH', defaultValue : 'main', description : 'release branch name')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: "${params.RELEASE_BRANCH}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/maruachi/spring-deployment.git']]])
            }
        }

        stage('Build') {
            steps {
                sh './gradlew bootjar'
            }
        }
    }

    post {
        success {
            sh '''#!/bin/bash
            function wait_boot_app {
                local target_ip=$1
                MAX_ATTEMPTS=60
                SLEEP_INTERVAL=3
                for ((i=1; i<=$MAX_ATTEMPTS; i++)); do
                    response=$(curl -s -o /dev/null -w "%{http_code}" "http://localhost:$target_ip")

                    if [ $response -eq 200 ]; then
                        break
                    else
                        echo "Attempt $i: Application server is not ready yet. Waiting for $SLEEP_INTERVAL seconds..."
                        sleep $SLEEP_INTERVAL
                    fi

                    if [ $i -eq $MAX_ATTEMPTS ]; then
                        echo "Max attempts reached. Exiting..."
                        exit 1
                    fi
                done
            }

            function main_process {
                if [ ! $(sudo systemctl is-active --quiet nginx) ]; then
                    sudo service nginx start
                fi

                BLUE_PORT=8081
                GREEN_PORT=8082
                if [ ! $(curl -s http://localhost:$BLUE_PORT) ] && [ ! $(curl -s http://localhost:$GREEN_PORT) ]; then
                    echo "Both Blue and Green is off."
                    echo "Start Blue app server"
                    nohup java -jar -DMYSQL_USERNAME=goodpass -DMYSQL_PASSWORD=Rntvotm1! ./build/libs/spring-deployment-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --port=$BLUE_PORT &
                    wait_boot_app $BLUE_PORT

                    sudo rm -rf /etc/nginx/sites-enabled/blue.conf
                    sudo rm -rf /etc/nginx/sites-enabled/green.conf
                    sudo ln -s /etc/nginx/sites-available/blue.conf /etc/nginx/sites-enabled/
                    sudo service nginx reload
                    return
                else
                    echo "At least one app server are running now."
                fi

                echo "Finding the healthy server..."
                if curl -s "http://localhost:$BLUE_PORT" > /dev/null; then
                    echo "Blue server is active now"
                    echo "Start Green app server"
                    sudo nohup java -jar -DMYSQL_USERNAME=goodpass -DMYSQL_PASSWORD=Rntvotm1! ./build/libs/spring-deployment-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --server.port=$GREEN_PORT &
                    wait_boot_app $GREEN_PORT

                    echo "Prepare to redirect nginx to Green port"
                    sudo ln -s /etc/nginx/sites-available/green.conf /etc/nginx/sites-enabled/
                    sudo rm -rf /etc/nginx/sites-enabled/blue.conf

                    echo "Start to switch port of nginx"
                    sudo service nginx reload

                    echo "Kill Blue app server"
                    sudo lsof -i :$BLUE_PORT | grep LISTEN | awk \'{print $2}\' | xargs -I {} sudo kill -9 {}
                else
                    echo "Green server is active now"
                    echo "Start Blue app server"
                    sudo nohup java -jar -DMYSQL_USERNAME=goodpass -DMYSQL_PASSWORD=Rntvotm1! ./build/libs/spring-deployment-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod --server.port=$BLUE_PORT &
                    wait_boot_app $BLUE_PORT

                    echo "Prepare to redirect nginx to Blue port"
                    sudo ln -s /etc/nginx/sites-available/blue.conf /etc/nginx/sites-enabled/
                    sudo rm -rf /etc/nginx/sites-enabled/green.conf

                    echo "Start to switch port of nginx"
                    sudo service nginx reload

                    echo "Kill Green app server"
                    sudo lsof -i :$GREEN_PORT | grep LISTEN | awk \'{print $2}\' | xargs -I {} sudo kill -9 {}
                fi
            }

            main_process
            '''
        }
    }
}