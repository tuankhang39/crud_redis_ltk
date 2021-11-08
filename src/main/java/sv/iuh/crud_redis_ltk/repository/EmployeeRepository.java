package sv.iuh.crud_redis_ltk.repository;

import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;
import sv.iuh.crud_redis_ltk.model.Employee;

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
        listOperations.rightPushAll("EMPLOYEE",employee.getId(),employee);
        System.out.print(employee.toString());
//        setOperations.add("EMPLOYEE",employee);
//       hashOperations.put("EMPLOYEE", employee.getId(), employee);
    }
    public List<Employee> findAll(){
//        listOperations.getOperations();
        return hashOperations.values("EMPLOYEE");
    }
    public Employee findById(Integer id){

        return (Employee) hashOperations.get("EMPLOYEE", id);
    }

    public void update(Employee employee){
        saveEmployee(employee);
    }
    public void delete(Integer id){
        hashOperations.delete("EMPLOYEE", id);
    }
}
