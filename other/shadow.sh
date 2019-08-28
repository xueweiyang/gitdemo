#！/bin/bash
#shadow.sh
ps -efww|grep -w 'python3'|grep -v grep|cut -c 9-15|xargs kill -9
python3 /home/f/soft/shadowsocksr/shadowsocks/local.py -c /home/f/soft/shadowsocksr/config.json
/etc/init.d/polipo restart
