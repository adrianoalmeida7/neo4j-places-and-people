select 
  distinct(t.id_place)
from
  travels t
where
  t.id_person in (
    
    select 
      t_in.id_person 
    from 
      travels t_in 
    where 
      t_in.id_place = 7
  ) 
  and t.id_place <> 7;

/* Inner join poupado por efeito de sanidade */