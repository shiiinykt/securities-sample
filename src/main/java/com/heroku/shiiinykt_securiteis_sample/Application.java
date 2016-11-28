package com.heroku.shiiinykt_securiteis_sample;



import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.heroku.shiiinykt_securiteis_sample.index.IndexController;
import com.heroku.shiiinykt_securiteis_sample.job.OrderJob;
import com.heroku.shiiinykt_securiteis_sample.login.LoginController;
import com.heroku.shiiinykt_securiteis_sample.order.OrderController;
import com.heroku.shiiinykt_securiteis_sample.reference.ReferenceController;

import static org.quartz.TriggerBuilder.newTrigger;
import static org.quartz.SimpleScheduleBuilder.repeatSecondlyForever;
import static org.quartz.JobBuilder.newJob;
import static spark.Spark.*;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

public class Application {

	
	public static void main(final String[] args) throws SchedulerException {
		init();
		setJob();
		
		port(Integer.valueOf(System.getenv("PORT")));

		staticFileLocation("/webroot"); 
		
		get(Meta.URL.LOGIN, LoginController.index);
		post(Meta.URL.LOGIN, LoginController.indexHandler);
		
		get(Meta.URL.INDEX, IndexController.index);
		
		before(Meta.URL.ORDER, Filters.auth);
		before(Meta.URL.ORDER + "/*", Filters.auth);
		get(Meta.URL.ORDER_INPUT, OrderController.input);
		post(Meta.URL.ORDER_INPUT, OrderController.inputHandler);
		get(Meta.URL.ORDER_CONFIRM, OrderController.confirm);
		post(Meta.URL.ORDER_CONFIRM, OrderController.confirmHandler);
		get(Meta.URL.ORDER_FINISH, OrderController.finsh);
		
		before(Meta.URL.REFERENCE + "/*", Filters.auth);
		get(Meta.URL.REFERENCE_ORDER, ReferenceController.order);

		
	}
	
	private static void init() {
		Guice.createInjector(new AbstractModule() {
			@Override
			protected void configure() {
				requestStaticInjection(LoginController.class);
				requestStaticInjection(IndexController.class);
				requestStaticInjection(OrderController.class);
				requestStaticInjection(ReferenceController.class);
				
				requestStaticInjection(OrderJob.class);
			}
		});
	}
	
	private static void setJob() throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        JobDetail jobDetail = newJob(OrderJob.class).build();
        Trigger trigger = newTrigger()
                .startNow()
                .withSchedule(repeatSecondlyForever(30))
                .build();
        scheduler.scheduleJob(jobDetail, trigger);
	}
}
