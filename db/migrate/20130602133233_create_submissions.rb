class CreateSubmissions < ActiveRecord::Migration
  def change
    create_table :submissions do |t|
    	t.references :location
    	t.references :user
      t.timestamps
    end
  end
end
