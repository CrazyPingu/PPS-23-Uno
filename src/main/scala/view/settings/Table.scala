package view.settings

import view.Label

import java.awt.{Color, Dimension}
import javax.swing.{JTable, SwingConstants}
import javax.swing.table.{DefaultTableCellRenderer, DefaultTableModel, TableColumn}

class Table(data: Array[Array[AnyRef]], columnNames: Array[AnyRef], columnSize: Array[Int])
    extends JTable(new DefaultTableModel(data, columnNames)):

  for i <- columnSize.indices do
    val column: TableColumn = this.getColumnModel.getColumn(i)
    column.setPreferredWidth(columnSize(i))

  setFocusable(false)
  setEnabled(false)
  setFont(getFont.deriveFont(20f))
  setIntercellSpacing(new Dimension(7, 7))
  setRowHeight(getRowHeight() + 20)
  applyPadding()

  private def applyPadding(): Unit =
    val renderer = new DefaultTableCellRenderer:

      override def getTableCellRendererComponent(
        table: JTable,
        value: Any,
        isSelected: Boolean,
        hasFocus: Boolean,
        row: Int,
        column: Int
      ): java.awt.Component =
        val component = new Label(value.toString)
        value match
          case "Achieved" =>
            component.setForeground(Color.GREEN)
          case "Not achieved" =>
            component.setForeground(Color.RED)
          case _ =>
            component.setBackground(Color.WHITE)
            component.setForeground(Color.BLACK)
        component.setHorizontalAlignment(SwingConstants.CENTER)
        component

    for i <- 0 until getColumnCount do getColumnModel.getColumn(i).setCellRenderer(renderer)
