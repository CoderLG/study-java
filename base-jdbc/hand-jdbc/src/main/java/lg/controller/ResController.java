package lg.controller;

import lg.domain.GFGX_Y_DMK_DMSJ;
import lg.service.DBHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * author: LG
 * date: 2019-09-04 11:00
 * desc:
 */
@RestController
public class ResController {

    @Autowired
    private DBHelper dbHelper;


    @Transactional
    @GetMapping(value = "/query")
    public List<GFGX_Y_DMK_DMSJ> queryDLBM() {

        System.out.println("transactional start");

        String selectStr = "SELECT *,st_astext(\"WZ\") FROM	\"GFGX_Y_DMK_DMSJ\" WHERE \"DLBM\" =500000200000052";

        List<GFGX_Y_DMK_DMSJ> dmsjs = dbHelper.pgQuery(selectStr, rs -> {

            return getDMSJList(rs,true);

        });
//        int inu = 1/0;   //没有实现事务
        System.out.println("transactional end");

        return dmsjs;
    }

    @Transactional
    @GetMapping(value = "/insertT")
    public String insertT() {


        String insertStr = "insert into geomtt values(222,33,'0103000020E61000000600000008000000AE9FC612C4DC4540C164B078E0D628C0EE9C4E2C7DDC4540AF72B0D248D828C0B79AEE6A44DC45400343500080D328C09592C68F75DB4540D6083055ABCD28C015992E811BDC454017151039E6CE28C069A2EEF008DD45408041B06A5BD328C0639F06CDBADC4540065B10FEECD528C0AE9FC612C4DC4540C164B078E0D628C0070000005A5D864721D6454089FC6F0B7ACC28C06D5EAE4F3CD64540B15CB04712D628C04856FEEE6BD545407010B0CF73CE28C03E4A4EB637D44540993F10C825D328C0C74A26D045D44540B411704C91CE28C0426096806ED6454065EF6F8E25CB28C05A5D864721D6454089FC6F0B7ACC28C006000000C99256817BDB454067E86F0474CA28C091882ED172DA454000FA6F2632CC28C0A98C8658DEDA45407A67F0C525D728C083840E750DDA4540764D50A390D428C08B80361DA7D945409FE7EF8C5CCA28C0C99256817BDB454067E86F0474CA28C006000000974E6697A7D44540E106ADFAB48028C0CF3A9F6D45EC454040D2EEDAA2AE28C0F0D896927DE245401A5CAF906ABC28C08E4BD6ED59D44540D338EF74E0B828C0EB375E5D64D245405F21CD6D5B8328C0974E6697A7D44540E106ADFAB48028C0060000009775FA21F53E4640F01B0A52FD3528C020A90AF41C444640D58C6E55AFA728C0F8B261177E2B464004BFCC277E7928C0B995115791284640FAE04BED4E6328C009684AB6993D464046932905562828C09775FA21F53E4640F01B0A52FD3528C006000000DAC7A42795AD4540FF4CBB80E5BA26C0D400956147B3454053BC80F2094627C0B7734536C6BE4540D498860F1ADC27C0483F44A5EE9F45403F1722CDB76827C066AB84F5BCAA4540B36ABB26E2BD26C0DAC7A42795AD4540FF4CBB80E5BA26C0')";

        String s = dbHelper.pgRun(insertStr);

        return s;

    }


    private ArrayList<GFGX_Y_DMK_DMSJ> getDMSJList(ResultSet rs, Boolean flag) {
        try {
            ArrayList<GFGX_Y_DMK_DMSJ> list = new ArrayList<GFGX_Y_DMK_DMSJ>();

            while (rs.next()) {
                GFGX_Y_DMK_DMSJ obj = new GFGX_Y_DMK_DMSJ();

                obj.DMBS = rs.getString("DMBS");
                obj.DMMC = rs.getString("DMMC");

                obj.WZ = rs.getString("st_astext");

                obj.LV=rs.getInt("LV");
                list.add(obj);
            }

            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
