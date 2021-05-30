<template>
  <div>
    <h1> TO-DO LIST</h1>
    <div>
        <input type="text" placeholder="Enter New Task here" v-model="task"/>
        <button @click="addTask"> Add to List </button>
    </div>
    <table>
      <tr>
        <th> Task </th>
        <th> Status </th>
        <th> Edit </th>
        <th> Delete </th>
      </tr>
      <tr v-for="(task, index) in tasks" :key="index">
        <td> {{ task.task }} </td>
        <td @click="changeStatus(index)"> {{ task.status }} </td>
        <td @click="editTask(index)"> E </td>
        <td @click="deleteTask(index)"> D </td>
      </tr>
    </table>
  </div>
</template>

<script>

export default {
  name: 'Todo',
  data: () => {
    return {
      task: '',
      editIndex: null,
      taskStatus: ['TO DO','In Progress','Complete'],
      tasks: [
      {
        task: 'get bananas from the shop',
        status: 'TO DO'
      },
      {
        task: 'Hit the GYM',
        status: 'Complete'
      },
      {
        task: 'Call Mike',
        status: 'TO DO'
      }
    ]
  };
  },
  methods: {
     addTask() {
      if(this.task.length === 0) return;

      if(this.editIndex === null)
      {
        this.tasks.push({
          task: this.task,
          status : 'todo'
        });
      }else{
        this.tasks[this.editIndex].task = this.task;
        this.editIndex = '';
      }
       
    },
    deleteTask(index){
      this.tasks.splice(index,1);
    },
    editTask(index){
      this.task = this.tasks[index].task;
      this.editIndex = index;
    },
    changeStatus(index){
      let statusIndex = this.taskStatus.indexOf(this.tasks[index].status);
      if(statusIndex<2){
        statusIndex++;
      }else{
        statusIndex=0;
      }
      this.tasks[index].status = this.taskStatus[statusIndex];
    }
  }
}
</script>
