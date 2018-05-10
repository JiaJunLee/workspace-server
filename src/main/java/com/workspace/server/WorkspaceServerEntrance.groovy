package com.workspace.server

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication
@EnableJpaAuditing
@EnableTransactionManagement
class WorkspaceServerEntrance {

    static void main(String[] args){
        SpringApplication.run(WorkspaceServerEntrance.class, args)
    }

}
