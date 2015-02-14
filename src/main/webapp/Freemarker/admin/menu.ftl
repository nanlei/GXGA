[
<#list menus as menu>
{ id: "menu${menu.categoryId}", text: "${menu.categoryName}"},
<#assign items=menu.categoryItems/>
<#list items as item>
{ id: "${item.categoryItemId}", pid: "menu${menu.categoryId}", text: "${item.categoryItemName}", url: "${item.categoryItemURL}", iconPosition: "top" }
<#if menu_has_next>,<#else><#if item_has_next>,</#if></#if>
</#list>
</#list> 
]