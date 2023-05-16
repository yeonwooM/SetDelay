package xyz.xynsia.setdelay;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SDListener implements Listener {
    private final SetDelay plugin; // SetDelay 클래스 인스턴스
    public SDListener(SetDelay plugin) { //SDListener 클래스의 생성자를 정의
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }
    /*셋업 로직, boolean값 (참과 거짓)을 가져오고 참일 시 컨피그 파일을 리로드한다.
    또한 해당 로직을 불러오면 플레이어의 무적시간 틱을 컨피그 값에서 불러온다.
    만약 exeption, 즉 컨피그 값이 없다면(예외사항) 콘솔에 해당 버그를 명시하고 컨피그 값을 지정하고 저장하여 준다.*/
    void setup(boolean deepSearch) {
        if (deepSearch) { // 참, 거짓 분류
            plugin.reloadConfig();
        }

        try {
            for (Player player : Bukkit.getOnlinePlayers()) { // 플레이어에게 반복
                player.setMaximumNoDamageTicks(Integer.parseInt(plugin.getConfig().getString("hit-delay"))); //힡딜 세팅
            }
        } catch (Exception e) { // 예외사항 캐치
            Bukkit.getConsoleSender().sendMessage("§4[NoHitDelay] 'hit-delay' has been reset, no valid 'hit-delay' set.");
            plugin.getConfig().set("hit-delay", 20); // 값 지정과 저장 구문
            plugin.saveConfig();
            for (Player player : Bukkit.getOnlinePlayers()) { // 플레이어에게 반복
                player.setMaximumNoDamageTicks(Integer.parseInt(plugin.getConfig().getString("hit-delay"))); // 힡딜 세팅
            }
        }
    }
    //엔티티셋업 로직, 일반 플레이어 셋업과 기능은 같다. 곧 해당 메소드들은 병합할 예정
    void setupEntities() {
        try {
            int hitDelay = Integer.parseInt(plugin.getConfig().getString("hit-delay"));
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.setMaximumNoDamageTicks(hitDelay);
                    }
                }
            }
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("§4[NoHitDelay] 'hit-delay' has been reset, no valid 'hit-delay' set.");
            plugin.getConfig().set("hit-delay", 20);
            plugin.saveConfig();
            for (World world : Bukkit.getWorlds()) {
                for (Entity entity : world.getEntities()) {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity) entity;
                        livingEntity.setMaximumNoDamageTicks(Integer.parseInt(plugin.getConfig().getString("hit-delay")));
                    }
                }
            }
        }
    }
    @EventHandler //이벤트핸들러
    public void onEntitySpawn(EntitySpawnEvent event) { //원하는 이벤트를 기반으로 하는 메소드 생성
        Entity entity = event.getEntity(); // 이벤트에 따른 엔티티나 스코어보드등의 정보를 가져와서 지정 가능
        if (entity instanceof LivingEntity) { // entity 가 instanceof (동일할떄 ) LivingEntity
            LivingEntity livingEntity = (LivingEntity) entity; // 지정
            int hitDelay = Integer.parseInt(plugin.getConfig().getString("hit-delay")); // 힟딜 값 가져오기
            livingEntity.setMaximumNoDamageTicks(hitDelay); // 힡딜 세팅
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        int hitDelay = Integer.parseInt(plugin.getConfig().getString("hit-delay"));
        event.getPlayer().setMaximumNoDamageTicks(hitDelay);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK) {
            Entity entity = event.getEntity();

            if (entity instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity) entity;
                livingEntity.setMaximumNoDamageTicks(20);

                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    player.setMaximumNoDamageTicks(20);
                }
                // 화염 데미지를 입는 상태에서 추가 틱 데미지를 막기 위한 캔슬구문
                event.setCancelled(true);
            }
        }
    }
}
