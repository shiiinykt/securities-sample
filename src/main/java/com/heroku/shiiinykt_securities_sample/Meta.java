package com.heroku.shiiinykt_securities_sample;

public class Meta {
	
	public class URL {
		public static final String APP_URL = "https://shiiinykt-securities-sample.herokuapp.com";
		public static final String LOGIN = "/login";
		public static final String LOGOUT = "/logout";
		public static final String INDEX = "/";
		public static final String ORDER = "/order";
		public static final String ORDER_INPUT = ORDER + "/input";
		public static final String ORDER_CONFIRM = ORDER + "/confirm";
		public static final String ORDER_FINISH = ORDER + "/finish";
		public static final String REFERENCE = "/reference";
		public static final String REFERENCE_ORDER = "/reference/order";
		public static final String CALLBACK = "/callback";
		public static final String LINE = "/line";
		public static final String LINE_REGISTORY = LINE + "/registory";
		public static final String ACCOUNT = "/account";
		public static final String ACCOUNT_REGISTORY = ACCOUNT + "/registory";
		
	}
	
	public class Parameter {
		public static final String ACCOUNT = "ac";
		public static final String ACCOUNT_NAME = "an";
		public static final String AMOUNT = "am";
		public static final String CODE = "co";
		public static final String DEPOSIT_TYPE = "dt";
		public static final String ID = "id";
		public static final String MESSAGE = "me";
		public static final String NAME = "na";
		public static final String ORDER_TYPE = "ot";
		public static final String PASSWORD = "pa";
		public static final String PIN = "pi";
		public static final String PRICE = "pr";
		public static final String QUERY = "qu";
		public static final String REDIRECT = "re";
		public static final String TRANZACTION = "tr";
	}
	
	public class Template {
		public static final String LAYOUT = "templates/layout.peb";
		public static final String CONVERT = "templates/convert.peb";
		public static final String INDEX = "templates/index/index.peb";
		public static final String LOGIN = "templates/login/index.peb";
		public static final String ORDER = "templates/order/index.peb";
		public static final String ORDER_INPUT = "templates/order/input.peb";
		public static final String ORDER_CONFIRM = "templates/order/confirm.peb";
		public static final String ORDER_FINISH = "templates/order/finish.peb";
		public static final String REFERENCE_ORDER = "templates/reference/order.peb";
		public static final String LINE_REGISTORY = "templates/line/registory.peb";
		public static final String ACCOUNT_REGISTORY = "templates/account/registory.peb";
		
	}
	
}
