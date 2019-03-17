<%@ attribute name="title" required="true" %>
<%@ attribute name="message" required="true" %>
<%@ attribute name="id" required="true" %>


<div id="${id}" title="${title}">
  <p>
    <span class="ui-icon ui-icon-circle-check" style="float:left; margin:0 7px 50px 0;"></span>
    ${message}
  </p>
</div>

<script>
    initializeDialog('#${id}');
</script>