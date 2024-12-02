import MainSelect from "../../../shared/Select/ui/MainSelect";

function SelectHub({handleChange}) {

    const list = [{id:1, name:"Hub1"}]

    return ( <MainSelect title="Выберите хаб" list={list} defaultValue={list[0].id} handleChange={handleChange} /> );
}

export default SelectHub;