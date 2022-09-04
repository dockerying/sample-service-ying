job "{{ $job_name }}" {
    [[ file "job.common.hcl"]]

   
    group "nginx" {
        count = {{ $nginx_count}}
        [[ file "group.common.hcl"]]

        task "nginx" {
            [[ file "task.common.hcl"]]
            config {
                image        = "{{ env "DOCKER_REGISTRY" }}/{{ $product_path }}/nginx:{{ $build_number }}"
                network_mode = "{{ $nginx_nw_mode }}"
                [[ file "registry.auth.hcl" ]]
            }
            env {
                [[ file "env.hcl" ]]
                # By specifying `0` we make nginx forward the / itself to this service.
                # In all normal cases you will start with PROXIED_SERVICE_1
                PROXIED_SERVICE_0="greeting"
                
                PROXIED_SERVICE_1="test"
                
            }
            resources {
                cpu    = {{ $nginx_cpu }} # MHz
                memory = {{ $nginx_memory }} # MB
                [[ file "network.common.hcl"]]
            }
        }
    }
    

    group "greeting" {
        count = {{ $service_count }}
        [[ file "group.common.hcl"]]
        task "greeting" {
            [[ file "task.common.hcl"]]
            config {
                image        = "{{ env "DOCKER_REGISTRY" }}/{{ $product_path }}/sample-09032022:{{ $build_number }}"
                network_mode = "{{ $service_nw_mode }}"
                [[ file "registry.auth.hcl" ]]
                
                privileged = true
                userns_mode = "host"
                
            }
            env {
                [[ file "env.hcl" ]]
                {{ if $is_dev }}
                
                  WAIT_FOR_SERVICE="{{ printf "%s-nfs" $job_name }}"
                  NFS_SERVER="{{ printf "%s-nfs.service" $job_name }}"
                  NFS_MOUNT_PARAMS="rw,nfsvers=3,nolock,proto=tcp,port=2049"
                  NFS_SRC_PATH="/local"
                  NFS_MOUNT_PATH="/sample-09032022"
                  #An else block is required for prod, leaving this empty as sampleapp mount path is not available in prod.
                  #Example vars to be used are shown below.
                  #WAIT_FOR_SERVICE="nfs.service"
                  #NFS_SRC_PATH = "/local"
                  #NFS_MOUNT_PATH = "/sampleapp"
                  LOG_LEVEL="debug"
                  
                {{ end }}
            }
            resources {
                cpu    = {{ $service_cpu }} # MHz
                memory = {{ $service_memory }} # MB
                [[ file "network.common.hcl"]]
            }
        }
    }
    
    group "test" {
        count = {{ $test_count }}
        [[ file "group.common.hcl" ]]
        task "test" {
            [[ file "task.common.hcl"]]
            config {
                image        = "{{ env "DOCKER_REGISTRY" }}/{{ $product_path }}/test:{{ $build_number }}"
                network_mode = "{{ $test_nw_mode }}"
                [[ file "registry.auth.hcl" ]]
            }
            env {
              SERVICE_NAME="{{ printf "%s-test" $job_name }}"
              [[ file "env.hcl" ]]
            }
            resources {
                cpu    = {{ $test_cpu }} # MHz
                memory = {{ $test_memory }} # MB
                [[ file "network.common.hcl"]]
            }
        }
    }
    

    
    group "nfs" {
        [[ file "restart.hcl" ]]
        count = {{ $nfs_count }}

        ephemeral_disk {
          size    = "500"
        }
        task "nfs" {
            [[ file "task.common.hcl"]]
            config {
                image        = "{{ env "DOCKER_REGISTRY" }}/{{ $product_path }}/nfs:{{ $build_number }}"
                network_mode = "{{ $nfs_nw_mode }}"
                [[ file "registry.auth.hcl" ]]
                privileged = true
                userns_mode = "host"
            }
            env {
              [[ file "env.hcl" ]]
            }
            resources {
                cpu    = {{ $nfs_cpu }} # MHz
                memory = {{ $nfs_memory }} # MB
                [[ file "network.common.hcl"]]
            }
        }
    }
    
    meta {
        product.name = "sample-09032022"
        product.descriptive-name = "sample-09032022 cobalt application"
        product.family = "Samples"
        product.type = "internal"
        product.front-door-url = "/greeting"
        product.health-check-url = "/health1,/health2"
        contact.primary = "renjith.pillai@sap.com"
        contact.secondary = "balaji.srinivasan01@sap.com"
        contact.pst = "sasanka.griddulur@sap.com"
        contact.ist = "sanjay.dc@sap.com"
        contact.team-dl = "DL_59B068535F99B786180000CF@sap.com"
        contact.slack-channel = "https://sap-ariba.slack.com/messages/C96L4NTTK"
        date.beta   = "31-Oct-2016"
        date.ga     = "31-Nov-2016"
        project.jira-project = "CBLT"
        doc.sre-cookbook = "https://wiki-ariba.sjc.sap.corp/display/COP"
        doc.project-wiki = "https://wiki-ariba.sjc.sap.corp/display/CDP"
        hana.schema = "schema1,schema2"
        remarks = <<REMARKS
                  This is a sample remarks section which
                  can span multiple lines. Use this section to add any
                  descriptive content for the Info sheet for the service.
        REMARKS
    }
}
