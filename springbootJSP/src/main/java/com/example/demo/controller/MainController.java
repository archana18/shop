package com.example.demo.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dao.ProductDAOImpl;
import com.example.demo.mail.Mail;
import com.example.demo.model.FeedbackBo;
import com.example.demo.model.Person;
import com.example.demo.model.ProductBo;
import com.example.demo.model.UserRegister;
import com.example.demo.service.UserService;

 
@Controller
public class MainController {
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
    private static List<Person> persons = new ArrayList<Person>();
 
    static {
        persons.add(new Person("Bill", "Gates"));
        persons.add(new Person("Steve", "Jobs"));
    }
    
  
 
    @RequestMapping(value = { "/personList" }, method = RequestMethod.GET)
    public String viewPersonList(Model model) {
 
        model.addAttribute("persons", persons);
 
        return "personList";
    }
    
   
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	private Set<ProductBo> buyNowList = new HashSet();
	private static final String PATH1 = "/error";
	@Autowired
	private ProductDAOImpl productDAO;

	@GetMapping("/index")
	public String index() {
		
		return "index";
	}

	@RequestMapping("/viewProduct")
	public ModelAndView viewProduct() {
		System.out.println(ANSI_GREEN + "VIEW PRODUCT!" + ANSI_RESET);
		List<ProductBo> list = productDAO.getProduct();
		logger.info("view product successfull");
		return new ModelAndView("viewProduct", "list", list);
	}
	@RequestMapping(value = "/cart/{productId}/{quantity}/", method = RequestMethod.POST)
	public ModelAndView cart(@PathVariable String productId, Integer quantity) {
		System.out.println("-------------------------------------------" + quantity);
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), quantity, productBo.getImage());
		logger.info("productList");

		buyNowList.add(productList);
		return new ModelAndView("redirect:/viewProduct");
	}

	@RequestMapping(value = "/Men")
	public ModelAndView men() {
		List<ProductBo> product = productDAO.getProductByCategory("men");
		logger.info("productList men " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductMen", product);
		mv.setViewName("Men");
		return mv;
	}

	@RequestMapping(value = "Women")
	public ModelAndView women() {
		List<ProductBo> product = productDAO.getProductByCategory("women");
		logger.info("productList women " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductWomen", product);
		mv.setViewName("Women");
		return mv;
	}

	@RequestMapping(value = "/Kid")
	public ModelAndView kid() {

		List<ProductBo> product = productDAO.getProductByCategory("Kids");
		logger.info("productList kid " + product);
		ModelAndView mv = new ModelAndView();
		mv.addObject("ProductKid", product);
		mv.setViewName("Kid");
		return mv;
	}

	@RequestMapping(value = "/buyNow/{productId}/{quantity}/", method = RequestMethod.POST)
	public ModelAndView buyNow(@PathVariable String productId, Integer quantity) {
		System.out.println("-------------------------------------------" + quantity);
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), quantity, productBo.getImage());
		logger.info("productList of buyNow " + productList);
		buyNowList.add(productList);
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyNowList", buyNowList);
		mv.setViewName("buyNow");
		return mv;
	}

	@RequestMapping("/buyNowOrder")
	public ModelAndView buyNowOrder() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("buyNowList", buyNowList);
		mv.setViewName("buyNowOrder");
		return mv;
	}

	@RequestMapping(value = "/confirmOrder", method = RequestMethod.POST)
	public String confirmOrder(HttpSession session) {

		if (session.getAttribute("username") != null) {
			buyNowList.clear();
			logger.info("order confirmed");
			String recipient = (String) session.getAttribute("emailId");
			logger.info("dynamic email id received" + recipient);
			Mail mail = new Mail();
			mail.sendMail("yes", session);
			return ("confirmOrder");
		} else {
			return ("Login");
		}
	}

	@RequestMapping(value = "/remove/{productId}/", method = RequestMethod.POST)
	public ModelAndView remove(@PathVariable String productId) {
		ProductBo productBo = productDAO.getProductInCart(productId);
		ProductBo productList = new ProductBo(productBo.getProductId(), productBo.getProductDesc(),
				productBo.getCategory(), productBo.getPrice(), productBo.getQuantity(), productBo.getImage());
		logger.info("product list" + productList);
		buyNowList.remove(productList);
		return new ModelAndView("redirect:/buyNowOrder");
	}
	
	private UserRegister userData = null;

	@Autowired
	private UserService userService;

	@Autowired(required = true)
	@Qualifier(value = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping(value = "/registerperson", method = RequestMethod.GET)
	public String registerPerson(Model model) {
		model.addAttribute("userRegister", new UserRegister());
		model.addAttribute("register", this.userService.listUsers());
		return "person";
	}

	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public String addPerson(@ModelAttribute("user") UserRegister p) {

		if (p.getId() == 0) {
			logger.info(" signup controller entered");

			this.userService.addPerson(p);
		} else {
			this.userService.updatePerson(p);
		}
		return "redirect:/userLogin";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(UserRegister userRegister, HttpSession session, Model model) {
		logger.info("User Data: " + userRegister);
		userData = userService.getPersonByEmail(userRegister, session);
		if (userData != null) {
			model.addAttribute("userData", userData);
			return new ModelAndView("redirect:viewProduct");
		} else {
			model.addAttribute("message", "Invalid Credentials, Please try again !!");
			return new ModelAndView("Login");
		}
	}

	@RequestMapping(value = "/logout")
	public String logout(UserRegister userRegister, HttpSession session, Model model) {
		logger.info("User Data: " + userRegister);
		if (session != null) {
			session.removeAttribute("username");
			session.invalidate();
		}
		return "redirect:/index.jsp";
	}

	@RequestMapping("/profile")
	public ModelAndView profile() {
		logger.info("profile entered controller");
		UserRegister user = userData;
		if (user != null) {
			ModelAndView mv = new ModelAndView();
			mv.addObject("user", user);
			mv.setViewName("editProfile");
			return mv;
		} else {
			return new ModelAndView("redirect:/userLogin");
		}
	}

	@RequestMapping("/editdata/{id}")
	public String editPerson(UserRegister userRegister, Model model) {

		UserRegister user = new UserRegister();
		model.addAttribute("user", this.userService.getUserById(userRegister));
		logger.info("user " + user);
		userData = null;
		return ("redirect:/profileUpdate.jsp");
	}

	@RequestMapping("/register")
	public ModelAndView register() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("register");
		return mv;
	}

	@RequestMapping("/userLogin")
	public ModelAndView userLogin() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("Login");
		return mv;
	}
	
	
	

	@RequestMapping(value = "/feedback", method = RequestMethod.POST)
	public String addFeedback(Model model, FeedbackBo feedback) {
		logger.info("feedback " + feedback);
		model.addAttribute("feedback", this.userService.addFeedback(feedback));
		return "redirect:/thanks.jsp";
	}
	
	@RequestMapping(value = "/error")
    public String error() {
        return "errorPage";
    }

	public String getErrorPath() {
        return PATH1;
    }
	
	
	@RequestMapping(value = "/contactUs")
	public String contact()
	{
		return "contactUs";
	}
	
	@RequestMapping(value = "/aboutUs")
	public String about()
	{
		return "aboutUs";
	}
	
	
    
 
}