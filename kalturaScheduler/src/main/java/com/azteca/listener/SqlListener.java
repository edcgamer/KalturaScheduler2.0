/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author SANTA
 */
package com.azteca.listener;
 

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
 
public class SqlListener implements JobListener {
 
	public  String LISTENER_NAME = "";

    public SqlListener(String name) {
        this.LISTENER_NAME=name;

    }
 
        
    @Override
    public String getName() {
        return LISTENER_NAME; //must return a name
    }


        
	// Run this if job is about to be executed.
	@Override
	public void jobToBeExecuted(JobExecutionContext context) {
 
		String jobName = context.getJobDetail().getKey().getName();
                String jobGroup = context.getJobDetail().getKey().getGroup();
//		System.out.println("jobToBeExecuted");
		System.out.println("Job : " + jobName + "of group "+jobGroup+" is going to start...");
 
	}
 
	// No idea when will run this?
	@Override
	public void jobExecutionVetoed(JobExecutionContext context) {
		System.out.println("jobExecutionVetoed");
	}
 
	//Run this after job has been executed
	@Override
	public void jobWasExecuted(JobExecutionContext context,
			JobExecutionException jobException) {
		System.out.println("jobWasExecuted");
 
		String jobName = context.getJobDetail().getKey().getName();
                String jobGroup = context.getJobDetail().getKey().getGroup();
//		System.out.println("Job : " + jobName + "of group "+jobGroup+" is finished...");
 
		if (!jobException.getMessage().equals("")) {
			System.out.println("Exception thrown by: " + jobName
				+ " Exception: " + jobException.getMessage());
		}
 
	}
 
}