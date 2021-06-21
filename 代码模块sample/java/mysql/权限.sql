--有访问机构的权限
select p.code,p.name from user_Role ur inner join role  r on ur.role_id = r.id 
inner join role_permission rp  on r.id = rp.role_id
inner join permission p  on p.id =  rp.permission_id
 where ur.user_id='180280773454077952'
order by p.code 


select * from user_organization where user_id ='180280773454077952'


SELECT id, tenant_id, user_organization_id, role_id, update_by, create_by, create_time, update_time
 FROM user_organization_role
 WHERE (user_organization_id IN (322326499075297280, 322329613671993344, 323110676343296000)) AND tenant_id = 'EMS_Default';


/*前端代码
       let code = this.permissionCode; //权限code如： ['003']
      getAccessOrgList(code).then(res => {
        if (res && res.code === 0) {
          this.orgList = res.data.sortBy('name');
          if (this.orgList.length == 1) {
            this.entityModal.orgId = this.orgList[0].id;
          }
          // console.log(res.data)
        }
      }).finally(() => {

      })

*/