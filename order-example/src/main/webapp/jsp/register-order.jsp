<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ taglib prefix="custom" tagdir="/WEB-INF/tags" %>

<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>


    <script src="<c:url value="/resources/js/register-order-script.js" />"></script>
    <script src="<c:url value="/resources/js/common.js" />"></script>

    <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/register-order.css" />'>
    <link rel="stylesheet" type="text/css" href='<c:url value="/resources/css/common.css" />'>

    <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Pedido</title>


</head>
<body>
    <custom:scripts />
    <custom:confirmDialog title="Confirma&ccedil;&atilde;o" message="Deseja confirmar essa opera&ccedil;&atilde;o"/>
    <custom:messageDialog title="Valida&ccedil;&atilde;o" message="Existe alguma informa&ccedil;&atilde;o inv&aacute;lida" id="message-product"/>


    <form:form id="regForm" modelAttribute="order" action="order-controller/save" method="post">
        <form:hidden path="id" id="id_order"/>

        <c:set var="deleted" value="${order.situation > 0}" />
        <c:set var="disabled" value="${order.id > 0 || deleted}" />

        <input type="hidden" id="orderDeleted" value="${deleted}"/>

        <div class="page-container">
            <div class="page-title"> Pedido </div>

            <div class="order-header">
                <div class="sub-title">Header</div>

                <div class="flex-layout">
                    <div class="flex-row">
                        <div class="flex-group">
                            <div class="flex-col"><form:label path="customer">Cliente</form:label></div>
                            <div class="flex-col">
                                <form:select path="customer.id" multiple="false" class="select-size" disabled="${disabled}">
                                    <form:options items="${customers}" itemValue="id" itemLabel="name" />
                                </form:select>
                            </div>
                        </div>

                        <div class="flex-group">
                            <div class="flex-col"><form:label path="date">Data</form:label></div>
                            <div class="flex-col"><form:input path="date" type="date" disabled="${disabled}"/></div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="order-itens">
                 <div class="sub-title">Itens</div>

                <div class="flex-layout">

                    <div class="flex-row">
                        <div class="flex-group">
                            <div class="flex-col"><label>Produto</label></div>
                            <div class="flex-col">
                                <select class="js-product select-size" ${deleted ? 'disabled="true"' :'' }">
                                    <c:forEach items="${products}" var="product">
                                        <option value="${product.id}">${product.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>

                        <div class="flex-group">
                            <div class="flex-col"><label>Quantidade</label></div>
                            <div class="flex-col"><input type="text" class="js-quantidade" ${deleted ? 'disabled="true"' :'' }"/></div>
                        </div>
                        <div class="flex-group">
                            <div class="flex-col"><button type="button" class="js-adicionar-produto" ${deleted ? 'disabled="true"' :'' }">Adicionar Produto </button></div>
                        </div>
                    </div>
                </div>

                 <div class="js-table-container"></div>
            </div>

            <div class="order-footer">
                <div>
                    <form:button id="register" name="register" disabled="${deleted}">Register</form:button>
                    <button type="button" class="js-delete-order" ${disabled == false || deleted ? 'disabled="true"' :'' }>Delete</button>
                </div>
            </div>
        </div>
    </form:form>

    <div class="page-footer">
        <a href="/order-example">Home</a> |
        <a href="order-list-controller">Lista de Pedidos</a> |
        <a href="order-controller">Novo Pedido</a>

    </div>
</body>
</html>