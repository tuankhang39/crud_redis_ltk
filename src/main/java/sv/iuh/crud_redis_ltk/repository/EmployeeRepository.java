package sv.iuh.crud_redis_ltk.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import sv.iuh.crud_redis_ltk.model.Employee;

import java.util.Arrays;
import java.util.List;

@Repository
public class EmployeeRepository {

    private HashOperations hashOperations;
    private ListOperations listOperations;
    private  SetOperations setOperations;
    private RedisTemplate redisTemplate;

    public EmployeeRepository(RedisTemplate redisTemplate) {

        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations=redisTemplate.opsForList();
        this.redisTemplate = redisTemplate;
        this.setOperations=redisTemplate.opsForSet();

    }

    public void saveEmployee(Employee employee){
//      listOperations.rightPush("LIST_EMPLOYEE", employee);
        setOperations.add("SET_EMPLOYEE", employee);
//      hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }
    public List<Employee> findAll(){
        //return listOperations.range("LIST_EMPLOYEE", 0, -1);
        //return hashOperations.values("EMPLOYEE");
        return Arrays.asList((Employee[]) setOperations.members("SET_EMPLOYEE").toArray());

    }
    public Employee findById(Integer id){
//      return (Employee) hashOperations.get("EMPLOYEE", id);
        List<Employee> list = findAll();
        for(Employee e : list) {
            if(e.getId() == id)
                return e;
        };

        return null;
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
        //listOperations.remove("LIST_EMPLOYEE", 1, findById(id));
        setOperations.remove("SET_EMPLOYEE", findById(id));
        //hashOperations.delete("EMPLOYEE", id);
    }
}
