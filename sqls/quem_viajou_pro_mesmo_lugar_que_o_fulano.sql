/* quem viajou pro mesmo lugar que o Paniz */

select 
  distinct(t.id_person),
  p.name
from 
  travels t inner join persons p on (
    t.id_person = p.id
  )
where 
  t.id_place in (
    select 
      t_in.id_place 
    from 
      travels t_in 
    where 
      id_person = 4
  ) 
  and t.id_person <> 6;
