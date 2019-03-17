<%@ attribute name="title" required="true" %>
<%@ attribute name="message" required="true" %>


<div id="confirm-dialog" title="${title}">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
    ${message}
  </p>
</div>


