package xyz.xynsia.setdelay;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class SetDelayTabCompleter implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) { //List형식의 탭 자동완성 메소드 구현 .. List는 객체들을 모아둔 집합의 개념(수정 가능)
        List<String> completions = new ArrayList<>();

        if (command.getName().equalsIgnoreCase("nohitdelay")) {
            if (args.length == 1) {
                completions.add("setDelay");
                completions.add("reloadConfig");
            }
        }
        return completions;
    }/*completions라는 이름의 ArrayList<String> 객체를 생성. 이 리스트는 문자열을 저장할 수 있는 동적 배열임
    command.getName() 메서드의 반환값이 "nohitdelay"와 대소문자를 무시한 경우에 해당하는지 체크
    만약 args가 1인 경우 completions 리스트에 "setDelay"와 "reloadConfig"라는 두 개의 문자열을 추가*/
}
