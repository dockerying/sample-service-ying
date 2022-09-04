Vagrant.configure(2) do |config|
  config.vm.hostname = "sample-service-ying-vm"
  config.vm.provider "docker" do |d|
      d.image = "docker-dev-repo.aws.ariba.com/ariba-cobalt-vagrant/vagrant:V-a83e4e0-38"
      # d.image = "2a722db58913"
      d.has_ssh = true
      d.privileged = true
      d.pull = true
      d.create_args = ["-v", "/var/run/docker.sock:/var/run/docker.sock"]
      d.ports = ["8500:8500", "8200:8200", "4646:4646", "9090:9090", "3500:3500", "8080:8080"]
  end

  #Keep this to reuse the maven dependencies that are getting downloaded. Saves a lot of time
  config.vm.synced_folder "~/.m2", "/home/vagrant/.m2", docker_consistency: "delegated"

  # Sync up the project folder from laptop host
  config.vm.synced_folder ".", "/home/vagrant/sample-service-ying", docker_consistency: "consistent"

  #Prepare the machine, do not remove this block
  config.vm.provision :shell, privileged: false, inline: <<-EOF
    sudo /usr/local/bin/post-startup.sh
  EOF

  #Switch Java versions if necessary. Uncomment as necessary.
  config.vm.provision :shell, inline: <<-EOF
    #switch-to-jdk11
    switch-to-jdk8
  EOF

  #Do the initial build of all components
  config.vm.provision :shell, privileged: false, inline: <<-EOF
    cd /home/vagrant/sample-service-ying
    ./bin/buildAll.sh
  EOF

  config.vm.provision "shell", privileged: false, inline: <<-EOF
    echo "Vagrant Box provisioned!"
    echo "Consul UI can be accessed at:  http://localhost:8500/ui"
    echo Once completed, the service can be accessed at the below links
    echo "      Sample app:  http://localhost:9090/sample-service-ying-dev/greeting"
    echo "      Test Rest End point to start test:  http://localhost:9090/sample-service-ying-dev-test/v1/start"
  EOF
end
