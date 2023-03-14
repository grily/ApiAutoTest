#!/usr/bin/env groovy
def desc_ip = "127.0.0.1" // 远程服务器的配置名称
def desc_path = "C:\\code\\ApiAutoTest" // 文件上传至服务器的目录
def app_name = "zw-admin" // 项目名称
def target_file = "${app_name}/target/*.jar" // 需要上传的文件（基于Jenkins的工作空间中的项目名）

pipeline { // Jenkins脚本的根节点，不能缺失
    agent any // 可以指定由哪个Jenkins节点执行（any 选择任意）
    options { // Jenkins的选项，可以配置构建的超时时间、日志输出时间等
        timestamps() // 日志输出时间（需要Build Timestamp Plugin 插件支持）
    }
    tools { // 配置打包用的工具
        maven 'Maven-3.8.1' // Maven-3.6.1是全局工具配置中的Maven名称
    }
    stages { // stages是Jenkins的阶段组，一个流水线可以包含多个阶段，由于在流水线中配置了Git，所以脚本中不需要写Git Clone的阶段，Jenkins会自动触发 Git Clone
            stage('start'){
                steps {
                      echo 'hello'
                }

            }
            stage('Build') { // 阶段一 使用Maven打包项目
                steps {
                    sh 'mvn clean '    //Linux上的命令
                }
            }
//         stage('Deploy') { // 阶段二 把打包后的Jar包上传至服务器，并执行Shell脚本(需要Publish Over SSH插件支持)
//             steps {
//                 sshPublisher(publishers: [sshPublisherDesc(configName: "${desc_ip}", transfers: [sshTransfer(cleanRemote: false, excludes: '', execCommand:
//                         """
//                         cd "${desc_path}"
//                         sh start.sh start
//                         """, execTimeout: 120000, flatten: true, makeEmptyDirs: false, noDefaultExcludes: false, patternSeparator: '[, ]+', remoteDirectory: "${desc_path}", remoteDirectorySDF: false, removePrefix: "", sourceFiles: "${target_file}")], usePromotionTimestamp: false, useWorkspaceInPromotion: false, verbose: true)])
//             }
//         }
//         stage('Clean Workspace') { // 阶段三 构建完成后清除工作空间
//             steps {
//                 cleanWs()
//             }
//         }
//     }
//     post { // 构建之后的操作
//           success { // 构建成功才执行
//             emailext ( // 发送构建成功的邮件
//                 subject: "【Jenkins】构建成功: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
//                 body: """<p>构建成功: ${env.JOB_NAME} [${env.BUILD_NUMBER}]:</p>
//                   <p>点击链接查看构建日志： <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
//                 recipientProviders: [buildUser()] // 邮件只发送给构建人
//               )
//           }
//           failure { // 构建失败才执行
//             emailext ( // 发送构建失败的邮件
//                 subject: "【Jenkins】构建失败: ${env.JOB_NAME} [${env.BUILD_NUMBER}]",
//                 body: """<p>构建失败: ${env.JOB_NAME} [${env.BUILD_NUMBER}]:</p>
//                   <p>点击链接查看构建日志： <a href='${env.BUILD_URL}'>${env.JOB_NAME} [${env.BUILD_NUMBER}]</a></p>""",
//                recipientProviders: [buildUser()] // 邮件只发送给构建人
//               )
//           }
      }
}
