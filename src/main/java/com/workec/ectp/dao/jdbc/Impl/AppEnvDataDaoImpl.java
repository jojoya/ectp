package com.workec.ectp.dao.jdbc.Impl;

import com.workec.ectp.dao.jdbc.IAppEnvDataDao;
import com.workec.ectp.entity.Bo.AppEnvDetailInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class AppEnvDataDaoImpl implements IAppEnvDataDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<AppEnvDetailInfo> findByEnvId(Integer envId) {
        String sql = "SELECT b.id as domainId,b.name as domainName, a.ip as ip,? as envId " +
                "FROM t_domain b LEFT JOIN  " +
                "(SELECT * from application_environment_detail t WHERE t.evn_id=?)a " +
                "ON  a.domain_id=b.id;";
        Object[] agrs = new Object[]{envId,envId};
        RowMapper<AppEnvDetailInfo> rowMapper = new BeanPropertyRowMapper(AppEnvDetailInfo.class);

        List<AppEnvDetailInfo> list = jdbcTemplate.query(sql,agrs,rowMapper);
//        List<AppEnvDetailInfo> list = jdbcTemplate.query(sql,agrs,new AppEnvDetailInfoRowMapper());
        if(list!=null && list.size()>0){
            return list;
        }else{
            return null;
        }
    }

}


/**
 * 行映射
 */
class AppEnvDetailInfoRowMapper implements RowMapper<AppEnvDetailInfo> {

    @Override
    public AppEnvDetailInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        AppEnvDetailInfo detailInfo = new AppEnvDetailInfo();
        detailInfo.setEnvId(rs.getInt("envId"));
        detailInfo.setDomainId(rs.getInt("domainId"));
        detailInfo.setIp(rs.getString("ip"));
        detailInfo.setDomainName(rs.getString("domainName"));
        return detailInfo;
    }


}
