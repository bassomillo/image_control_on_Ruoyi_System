//package com.ruoyi.project.cmd;
//
//
//import org.activiti.engine.impl.cmd.NeedsActiveTaskCmd;
//import org.activiti.engine.impl.interceptor.CommandContext;
//import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
//import org.activiti.engine.impl.persistence.entity.TaskEntity;
//import org.activiti.engine.impl.persistence.entity.TaskEntityManagerImpl;
//
///**
// * by dong
// * activiti删除当前运行时任务命令，并返回当前任务的执行对象id
// * 这里继承了NeedsActiveTaskCmd，主要时很多跳转业务场景下，要求不能时挂起任务。可以直接继承Command即可
// */
//
//public class DeleteTaskCmd extends NeedsActiveTaskCmd<String> {
//    private String jumpReason;
//    public DeleteTaskCmd(String taskId ,String jumpReason){
//        super(taskId);
//        this.jumpReason = jumpReason;
//    }
//    public String execute(CommandContext commandContext, TaskEntity currentTask){
//        //获取所需服务
//        TaskEntityManagerImpl taskEntityManager = (TaskEntityManagerImpl)commandContext.getTaskEntityManager();
//        //获取当前任务的来源任务及来源节点信息
//        ExecutionEntity executionEntity = currentTask.getExecution();
//        //删除当前任务,来源任务
//        taskEntityManager.deleteTask(currentTask, jumpReason, false, false);
//        return executionEntity.getId();
//    }
//    public String getSuspendedTaskException() {
//        return "挂起的任务不能跳转";
//    }
//}
