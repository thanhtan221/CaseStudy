package com.cg.controller;

import com.cg.model.Customer;
import com.cg.service.CustomerService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(value = "/cart")
public class CartCustomerServlet extends HttpServlet {
    CustomerService customerService = new CustomerService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "cartcustomer":
                cartcustomer(request, response);
                break;
            case "editcart":
                ShowEdit(request, response);
                break;
            case"deletedcart":
                ShowDeleted(request,response);
            default:
                listCart(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action == null) {
            action = "";
        }
        switch (action) {
            case "editcart":
                EditCart(request, response);
                break;
            case "deletedcart":
                Deleted(request,response);
                break;
            default:
                listCart(request, response);
                break;
        }
    }

    private void cartcustomer(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/CartCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void listCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Customer> customerList = customerService.SeleCart();
        totalCart(request, response);
        request.setAttribute("customerList", customerList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/CartCustomer.jsp");
        dispatcher.forward(request, response);
    }

    private void totalCart(HttpServletRequest request, HttpServletResponse response) {
        List<Customer> customerLists = customerService.SELECTTotal();
        request.setAttribute("customerLists", customerLists);
    }

    private void EditCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int color = 1;
        String message;
        int id = Integer.parseInt(request.getParameter("id"));
        long quantity = Long.parseLong(request.getParameter("quantity"));
        Customer customer = new Customer(id, quantity);
        if (quantity <= 0) {
            color = 0;
            request.setAttribute("color", color);
            message = "QuanTiTy Must Be Greater Than 0 ";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer/EditCustomer.jsp");
            request.setAttribute("customerList", customer);
            dispatcher.forward(request, response);
        } else {
            request.setAttribute("color", color);
            message = "Edit Success";
            request.setAttribute("message", message);
            customerService.EditCustomer(customer);
            RequestDispatcher dispatcher = request.getRequestDispatcher("customer/EditCustomer.jsp");
            request.setAttribute("customerList", customer);
            dispatcher.forward(request, response);
        }
    }

    private void ShowEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.ShowEdit(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/EditCustomer.jsp");
        request.setAttribute("customerList", customer);
        dispatcher.forward(request, response);
    }

    private void ShowDeleted(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Customer customer = customerService.ShowEdit(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/DeleteCustomer.jsp");
        request.setAttribute("customerList", customer);
        dispatcher.forward(request, response);

    }
    private void Deleted(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        customerService.deleteCustomer(id);
        List<Customer> customerList = customerService.SeleCart();
        RequestDispatcher dispatcher = request.getRequestDispatcher("customer/CartCustomer.jsp");
        request.setAttribute("customerList", customerList);
        dispatcher.forward(request, response);

    }
}
