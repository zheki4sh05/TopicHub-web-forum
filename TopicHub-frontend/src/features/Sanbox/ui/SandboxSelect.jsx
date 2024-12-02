import MainSelect from "../../../shared/Select/ui/MainSelect";
import { useDispatch, useSelector } from "react-redux";
import {
  getSandboxComponents,
  getSandboxList,
  saveItem,
} from "../model/sandboxSlice";
function SandboxSelect() {
  const list = useSelector(getSandboxComponents);
 
  const dispatch = useDispatch();

  const handleChange = (id) => {
    let item = list.find((c) => c.id === id);
    if(item.id!=0){
        dispatch(saveItem({created: new Date().getTime(), ...item}));
    }
   
  };

  return (
    <MainSelect
      list={list}
      title={"Часть статьи"}
      defaultValue={list[0].id}
      handleChange={handleChange}
      reset={true}
    />
  );
}

export default SandboxSelect;
