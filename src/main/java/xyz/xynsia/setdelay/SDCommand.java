package xyz.xynsia.setdelay;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SDCommand implements CommandExecutor {
    private final SetDelay plugin; // SetDelay 클래스 인스턴스
    private SDListener sdListener; //SDListener 클래스 인스턴스

    public SDCommand(SetDelay plugin) { //SDCommand 클래스의 생성자 정의
        this.plugin = plugin;
    }

    // 명령어 처리
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) { //커맨드 메소드 도입부
        if (command.getName().equalsIgnoreCase("nohitdelay")) { //가져온 커맨드값이 nohitdelay와 같다면
            if (args.length >= 1) { //args의 길이가 1 이상이라면 통과 아니라면 42번 줄 실행
                if (args.length == 2 && args[0].equalsIgnoreCase("setdelay")) { //arg의 길이가 2고, arg 0이 setdelay라면,
                    try {
                        Integer.parseInt(args[1]); // arg 1을 정수값으로 타입변환
                    } catch (Exception e) { //예외상황 캐치 (try의 구문, 타입변환이 안된다면 (arg1이 숫자가 아닐 떄)
                        sender.sendMessage("§c" + args[1] + "은(는) 숫자가 아닙니다.");
                        return false;
                    }

                    int delay = Integer.parseInt(args[1]); //여기서부터 힡딜세팅 구문
                    plugin.getConfig().set("hit-delay", delay);
                    plugin.saveConfig();
                    sender.sendMessage("§6[§aNoHitDelay§6] §f히트 딜레이를 " + delay + "으로 변경하였습니다.");
                    sdListener.setup(true);
                    sdListener.setupEntities();
                } else if (args.length == 1 && args[0].equalsIgnoreCase("reloadConfig")) { // 여기부터는 앞을 제대로 읽었으면 이해가 되야함
                    sender.sendMessage("§2[§aNoHitDelay§2] §8설정을 다시 불러왔습니다.");
                    plugin.reloadConfig();
                    sdListener.setup(true);
                    sdListener.setupEntities();
                } else {
                    sender.sendMessage("§0> §a/nohitdelay§8 setDelay §6<§bdelay in ticks§6>");
                    sender.sendMessage("§0> §a/nohitdelay§8 reloadConfig");
                }
            } else {
                sender.sendMessage("§0> §a/nohitdelay§8 setDelay §6<§bdelay in ticks§6>");
                sender.sendMessage("§0> §a/nohitdelay§8 reloadConfig");
            }
        }
        return true;
    }
}

