# Production overrides
{{ $service_count := "6" }}
{{ $service_cpu := "1200" }}
{{ $service_memory := "4096" }}
{{ $service_nw_mode := printf "sampleApp-app-epg/%s" $tenant }}

{{ $nginx_nw_mode := printf "sampleApp-web-epg/%s" $tenant }}
{{ $nginx_count := "2" }}
{{ $nginx_memory := "200" }}
{{ $nginx_cpu := "500" }}




{{ $test_nw_mode := printf "sampleApp-app-epg/%s" $tenant }}
{{ $test_count := "0" }}



{{ $nfs_nw_mode := printf "sampleApp-app-epg/%s" $tenant }}
{{ $nfs_count := "0" }}



{{ $is_vagrant := false }}
{{ $is_dev := false }}
