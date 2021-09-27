class Expression {
    def field
    def op
    def value

    def call = {
        println(this)
        println(owner)
        println(delegate)
        def v = {
            println(this)
            println(owner)
            println(delegate)
        }
        v()
    }

    private String inner() {
        "EXP[$field $op $value]"
    }

    def match(map) {
        map[field] == value
    }

    def methodMissing(String name, args) {
        println("name=$name, args=$args")
    }

    static void main(args) {
        def exp = new Expression(field: "id", op:"=", value:111)

        // 动态访问属性
        println exp.getProperty("value")
        exp.setProperty("value", 123)
        def valueProp = "value"
        println "exp[$valueProp] = ${exp[valueProp]}"
        println "exp.\"$valueProp\" = " + exp."$valueProp"

        // 轻松调用私有方法
        println exp.invokeMethod('inner', null)
        println exp.invokeMethod('match', [id: 123])

        exp.call()

        exp.unknown('haha')
    }

    node(nodelabel){
        stage('Pull') {
            try {
                checkout([$class: 'GitSCM', branches: [[name: '${branch_name}']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[credentialsId: '${git_credentialsId}', refspec: '+refs/heads/${branch_name}:refs/remotes/origin/${branch_name}', url: '${git_addr}']]])
                sh '/bin/sh /data/paas/ione_var.sh pull ${deploy_id} true ${BUILD_URL}console'
            } catch(err) {
                sh '/bin/sh /data/paas/ione_var.sh pull ${deploy_id} false ${BUILD_URL}console && exit 1'
            }
        }
        stage('Replace configure') {
            try {
                if (env.replace_config_dir != 'false') {
                    sh '/bin/sh /data/paas/replace_config.sh ${pkg_name} ${WORKSPACE} ${replace_config_dir}'
                } else {
                    echo 'Dont need to Replace file.'
                }
            } catch(err) {
                sh '======== cp file error, no such dir, please check!!! ========='
            }
        }
        stage('Build') {
            try {
                sh '${mvn_args} -U -Dmaven.repo.local="/data/iqianjin/${nginx_ip}/.m2"'
                sh '/bin/sh /data/paas/ione_var.sh package ${deploy_id} true ${BUILD_URL}console'
            } catch(err) {
                sh '/bin/sh /data/paas/ione_var.sh package ${deploy_id} false ${BUILD_URL}console && exit 1'
            }
        }
        stage('Deploy') {
            try {
                sh '/bin/sh /data/paas/deploy.sh'
                sh '/bin/sh /data/paas/ione_var.sh develop ${deploy_id} true ${BUILD_URL}console'
            } catch (err) {
                sh '/bin/sh /data/paas/ione_var.sh develop ${deploy_id} false ${BUILD_URL}console && exit 1'
            }
        }
    }
}