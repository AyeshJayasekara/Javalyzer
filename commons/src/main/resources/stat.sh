while true; do (uptime && echo ----- && free -m && echo =====) >> statistics.log; sleep 1; done
