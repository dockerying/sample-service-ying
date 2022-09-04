# Dev overrides
{{ $service_count := "1" }}
{{ $service_nw_mode := "bridge" }}

{{ $nginx_nw_mode := "bridge" }}
{{ $nginx_count := "1" }}



{{ $test_count := "1" }}
{{ $test_nw_mode := "bridge" }}



{{ $nfs_count := "1" }}
{{ $nfs_nw_mode := "bridge" }}


{{ $is_vagrant := false }}
{{ $is_dev := true }}
